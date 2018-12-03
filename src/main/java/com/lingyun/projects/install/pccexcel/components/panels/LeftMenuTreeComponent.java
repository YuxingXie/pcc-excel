package com.lingyun.projects.install.pccexcel.components.panels;

import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelDataRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.route.JPanelRouter;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LeftMenuTreeComponent extends TopComponent {
    private JTree tree;
    private ExcelService excelService;
    private ExcelDataRepository excelDataRepository;
    private JPanelRouter observer;
    private JPopupMenu popupMenu;
    private DefaultMutableTreeNode top ;
    private DefaultMutableTreeNode node1;
    private DefaultMutableTreeNode node2;
    private DefaultMutableTreeNode node2_1;
    private DefaultMutableTreeNode node2_2;
    private Excel popMenuExcel;
    private DefaultTreeModel model;
    public LeftMenuTreeComponent(ExcelService excelService, ExcelDataRepository excelDataRepository, JPanelRouter observer) {
        this.excelService=excelService;
        this.excelDataRepository=excelDataRepository;
        this.observer=observer;
        top = new DefaultMutableTreeNode("系统菜单");
        node1 = new DefaultMutableTreeNode("文件列表");
        node2 = new DefaultMutableTreeNode("人员与分类");
        node1.setAllowsChildren(true);
        node2.setAllowsChildren(true);
        node2_1 = new DefaultMutableTreeNode("人员管理",false);
        node2_2 = new DefaultMutableTreeNode("分类管理",false);


        addLeftTree();
        this.observer.addAlwaysRefreshComponent(this);
    }

    public JTree getTree() {
        return tree;
    }

    @Override
    public void loadData() {


//        root.removeAllChildren();
        java.util.List<Excel> excelList=excelService.finAll();



        while (node1.getChildCount()>0){
            DefaultMutableTreeNode child = (DefaultMutableTreeNode)node1.getChildAt(node1.getChildCount()-1);
            model.removeNodeFromParent(child);
        }
        int i=0;
        for(Excel excel:excelList){
            DefaultMutableTreeNode excelNode=new DefaultMutableTreeNode(excel);
            excelNode.setAllowsChildren(false);
//            node1.add(excelNode); no,it's wrong
            model.insertNodeInto(excelNode,node1,node1.getChildCount());
            i++;
            if(i<excelList.size()) continue;
            TreeNode[] treeNodes=model.getPathToRoot(excelNode);
            TreePath treePath = new TreePath(treeNodes);
            tree.makeVisible(treePath);

        }

    }
    private void addLeftTree() {

        model = new DefaultTreeModel(top) ;

        java.util.List<Excel> excelList=excelService.finAll();
        for(Excel excel:excelList){
            DefaultMutableTreeNode excelNode=new DefaultMutableTreeNode(excel);
            excelNode.setAllowsChildren(false);
//            node1.add(excelNode);
            model.insertNodeInto(excelNode,node1,node1.getChildCount());

        }

        node2.add(node2_1);
        node2.add(node2_2);
        top.add(node1);
        top.add(node2);

        model.setAsksAllowsChildren(true);

        tree = new JTree(model){
            @Override
            public TreeCellRenderer getCellRenderer() {
                DefaultTreeCellRenderer renderer=new DefaultTreeCellRenderer();

                return renderer;
            }
        };
        tree.setRowHeight(24);

//        tree.setRootVisible(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);


        tree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (node == null)
                    return;
//                tree.expandPath(new TreePath(node.getPath()));
                tree.setSelectionPath(new TreePath(node.getPath()));
                Object object = node.getUserObject();
                if (node.isLeaf()) {
                    if("人员管理".equals(object.toString())){
                        System.out.println(object);
                        observer.navigateTo("personPanel");
                    }else if("分类管理".equals(object.toString())){
                        observer.navigateTo("groupManagerPanel");
                    }else {
                        if(object instanceof Excel){
                            Constant.currentExcel=(Excel) object;
                            observer.navigateTo("excelPanel");

                        }
                    }
                }


            }
        });




        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {

                    TreePath tp=tree.getPathForLocation(e.getX(),e.getY());
                    if (tp != null) {

                        DefaultMutableTreeNode node =  (DefaultMutableTreeNode)tp.getLastPathComponent();
                        Object object =node.getUserObject();
                        if (object instanceof  Excel){
                            Excel excel = (Excel) object;
                            LeftMenuTreeComponent.this.popMenuExcel=excel;
                            popupMenu.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                }
            }
        });



        JMenuItem mPreview, mDel,mExport;
        popupMenu = new JPopupMenu();

        mDel = new JMenuItem("删除");
        mPreview = new JMenuItem("预览");
        mExport = new JMenuItem("导出");
        mPreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                observer.navigateTo("excelExportReviewPanel");
            }
        });
        mDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LeftMenuTreeComponent.this.excelService.delete(LeftMenuTreeComponent.this.popMenuExcel);
                LeftMenuTreeComponent.this.loadData();
            }
        });
        mExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponentsDrawTools.exportExcel(LeftMenuTreeComponent.this.excelDataRepository.findByExcel(popMenuExcel),LeftMenuTreeComponent.this.excelService,LeftMenuTreeComponent.this);
            }
        });
        popupMenu.add(mDel);
        popupMenu.add(mPreview);
        popupMenu.add(mExport);
    }




}
