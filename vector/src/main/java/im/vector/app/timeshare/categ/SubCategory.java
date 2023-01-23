package im.vector.app.timeshare.categ;

public class SubCategory {
    String sub_categ_name;
    int categ_posi;
    String categ_name;
    private boolean isSelected = false;

    public SubCategory(String sub_categ_name, int categ_posi, String categ_name) {
        this.sub_categ_name = sub_categ_name;
        this.categ_posi = categ_posi;
        this.categ_name = categ_name;
    }


    public String getSub_categ_name() {
        return sub_categ_name;
    }


    public int getCateg_posi() {
        return categ_posi;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
