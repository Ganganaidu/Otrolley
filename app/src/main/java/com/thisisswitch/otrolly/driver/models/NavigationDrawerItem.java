package com.thisisswitch.otrolly.driver.models;

/**
 *
 * @author Raja Gopal Reddy P
 */
public class NavigationDrawerItem {

    private boolean showNotify;
    private String title;
    private int icon;


    public NavigationDrawerItem() {

    }

    public NavigationDrawerItem(boolean showNotify, String title, int icon) {
        this.showNotify = showNotify;
        this.title = title;
        this.icon = icon;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
