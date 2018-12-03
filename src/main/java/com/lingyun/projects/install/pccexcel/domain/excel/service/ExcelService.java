package com.lingyun.projects.install.pccexcel.domain.excel.service;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.string.StringUtils;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelData;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelRepository;
import com.lingyun.projects.install.pccexcel.domain.person.entity.Person;
import com.lingyun.projects.install.pccexcel.domain.person.repo.PersonRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

@Service
public class ExcelService {

    @Resource
    private ExcelRepository excelRepository;
    @Resource
    private ExcelDataRepository excelDataRepository;
    @Resource
    PersonRepository personRepository;
    public Excel getCurrentExcel(){
        return null;
    }

    public Excel findByFilePath(String absolutePath) {
        List<Excel> excelList = excelRepository.findByPath(absolutePath);
        assert (BeanUtil.emptyCollection(excelList)||excelList.size()==1);

        return BeanUtil.emptyCollection(excelList)?null:excelList.get(0);
    }
    public Excel findByLastOpenDateGreatest(){
        return excelRepository.findByLastOpenDateGreatest();
    }
    private Person getPersonFromName(String name,List<Person> people){//不检查空字符串
        if (BeanUtil.emptyCollection(people)) return null;
//        if (StringUtils.isBlank(name)) return null;
        for (Person person:people){
            if (StringUtils.isBlank(person.getName()))continue;
            if (person.getName().trim().equals(name.trim())) return person;
        }
        return null;
    }
    @Transactional
    public List<ExcelData> saveExcelData(Excel excel) {
        SortedMap<String, List<List<Object>>> sortedMap=excel.toSortedMap();
        List<ExcelData> excelDataList=new ArrayList<>();
        List<Person> people=personRepository.findAll();
        int sheetIndex=0;
        for(String sheetName:sortedMap.keySet()){
            int order=0;
            sheetIndex++;
            List<List<Object>> sheetEntries=sortedMap.get(sheetName);
            for(List<Object> entry:sheetEntries){
                order++;
                if (order==1) continue;//第一行是表头，忽略
                ExcelData excelData = new ExcelData();
                excelData.setExcel(excel);
                excelData.setOrder(order-1);
                excelData.setSheetIndex(sheetIndex);
                excelData.setSheetName(sheetName);
                Person person=getPersonFromName(entry.get(0).toString(),people);
                if (person==null&&entry.get(0)!=null){
                    person=new Person();
                    person.setName(entry.get(0).toString());
                    person=personRepository.save(person);
                }
                excelData.setPerson(person);
                try {
                    excelData.setLoginCount(entry.get(1)==null?null:Integer.parseInt(entry.get(1).toString()));
                    excelData.setViewCount(entry.get(2)==null?null:Integer.parseInt(entry.get(2).toString()));
                    excelData.setPraiseCount(entry.get(3)==null?null:Integer.parseInt(entry.get(3).toString()));
                    excelData.setCommentCount(entry.get(4)==null?null:Integer.parseInt(entry.get(4).toString()));
                    excelData.setShareCount(entry.get(5)==null?null:Integer.parseInt(entry.get(5).toString()));
                }catch (Exception e){//不能转换的数字，数字过大等等
                    e.printStackTrace();
                }
                excelDataList.add(excelData);

            }
        }
        return excelDataRepository.save(excelDataList);
    }
    @Transactional
    public Excel save(Excel excel) {
        excel=excelRepository.save(excel);
        int d=excelDataRepository.deleteAllByExcel(excel);
        System.out.println("删除 "+d+" 条");
        saveExcelData(excel);
        return excel;
    }

    public List<Excel> finAll() {
        return excelRepository.findAll();
    }

    @Transactional
    public void delete(Excel excel) {
        excelDataRepository.deleteAllByExcel(excel);
        excelRepository.delete(excel);
    }
}
