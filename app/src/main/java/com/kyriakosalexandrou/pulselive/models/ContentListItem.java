
package com.kyriakosalexandrou.pulselive.models;

public class ContentListItem {
    private Integer id;
    private String title;
    private String subtitle;
    private String date;

    public ContentListItem(Integer id, String title, String subtitle, String date) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.date = date;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }
}
