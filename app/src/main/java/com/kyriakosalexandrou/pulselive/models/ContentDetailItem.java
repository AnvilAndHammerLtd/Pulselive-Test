
package com.kyriakosalexandrou.pulselive.models;

public class ContentDetailItem {
    private Integer id;
    private String title;
    private String subtitle;
    private String body;
    private String date;

    public ContentDetailItem(Integer id, String title, String subtitle, String body, String date) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.body = body;
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
     * @return The body
     */
    public String getBody() {
        return body;
    }

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }
}
