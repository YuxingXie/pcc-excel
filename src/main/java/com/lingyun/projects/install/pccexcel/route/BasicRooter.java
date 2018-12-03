package com.lingyun.projects.install.pccexcel.route;

import java.util.*;

/**
 * 观察者实现类
 * @param <T>
 */
public abstract class BasicRooter<T> implements Router {

    private int current=0;
    private SortedMap<String,T> routerPointMap=new TreeMap<>();
    @Override
    public void navigateTo(String to) {
        if(to==null) return;
        int i=0;
        for(String routerPointName: routerPointMap.keySet()){
            if(to.equals(routerPointName)){
                this.current=i;
//                System.out.println("to :"+to+",index:"+this.current);
                renderView();
                return;
            }
            i++;
        }
        this.current=0;

        renderView();
    }

    @Override
    public void back() {
        this.current--;
        if (this.current==-1) this.current=this.routerPointMap.size()-1;
        System.out.println("back to "+this.current);
        renderView();
    }
//
    @Override
    public void forward() {
        this.current++;
        if (this.current==this.routerPointMap.size()) this.current=0;
//        System.out.println("forward to "+this.current);
        renderView();
    }

    public T getCurrentRouterPoint() {
        int i=0;
        for(String routerPointName: routerPointMap.keySet()){
            if(current==i){
                System.out.println("current component name:"+routerPointName);
                return routerPointMap.get(routerPointName);
            }
            i++;
        }
//        System.out.println("current component name:"+routerPointMap.firstKey());
        return routerPointMap.get(routerPointMap.firstKey());
    }
    public void addRouterPoint(String name,T routerPoint){
        routerPointMap.put(name,routerPoint);
    }

    public void renderView(){
            T currentPanel=getCurrentRouterPoint();
            SortedMap<String, T> panelMap=getRouterPointMap();
            for(Map.Entry<String,T> entry:panelMap.entrySet()){
                if (currentPanel==entry.getValue()) {
                    show(currentPanel);
                }else {
                    hide(entry.getValue());
                }
            }
    }
    public abstract void show(T t);
    public abstract void hide(T t);
    public SortedMap<String, T> getRouterPointMap() {
        return routerPointMap;
    }

}
