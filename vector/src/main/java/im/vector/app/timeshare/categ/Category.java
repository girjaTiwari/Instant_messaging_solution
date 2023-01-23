package im.vector.app.timeshare.categ;

public class Category {
    int image;
    String category;
    private boolean isSelected = false;

    public Category(int image, String category) {
        this.image = image;
        this.category = category;
    }

    public int getImage() {
        return image;
    }


    public String getCategory() {
        return category;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
