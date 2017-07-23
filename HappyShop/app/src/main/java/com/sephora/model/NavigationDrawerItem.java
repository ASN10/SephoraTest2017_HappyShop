package com.sephora.model;

/**
 * Created by nivedhitha.a on 4/25/2016.
 */
public class NavigationDrawerItem {

    private String categoryName;
    private int icon;

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public void setCategory(boolean category) {
        isCategory = category;
    }

    private String categoryKey;

    public boolean isCategory() {
        return isCategory;
    }

    public void setIsCategory(boolean isCategory) {
        this.isCategory = isCategory;
    }

    private boolean isCategory;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
