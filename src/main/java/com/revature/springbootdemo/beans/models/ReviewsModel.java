package com.revature.springbootdemo.beans.models;

import javax.persistence.*;
import java.sql.Time;

@Entity(name = "review_model")
public class ReviewsModel {

    //  Fields
    @Id
    @Column(name = "review_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ReviewID;

    //@ForeighKey(ReferencingTableName = "Reviews", ReferencedTableName = "Users", PrimaryKey = "id")
    @Column(name = "user_id")
    private Integer ID;

    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    private int rating;

    @Column(name = "time")
    private Time time;

    //@ForeighKey(ReferencingTableName = "Reviews", ReferencedTableName = "Reviews", PrimaryKey = "Review_ID")
    //@Property(fieldName = "ReplyToReview")
    @Column(name = "reply_to_Review")
    private int ReplyToReview;

    //@ForeighKey(ReferencingTableName = "Reviews", ReferencedTableName = "Location", PrimaryKey = "Location_ID")
    //@Property(fieldName = "Location_ID")
    @Column(name = "location_id")
    private int LocationID;


    public ReviewsModel() {      // no arg Constructor
    }

    public ReviewsModel(int ReviewID, int ID, String content, int rating, int Location_Id, Time time, int ReplyToReview) {

        setReviewD(ReviewID);
        setID(ID);
        setContent(content);
        setRating(rating);
        setLocationID(Location_Id);
        setTime(time);
        setReplyToReview(ReplyToReview);
    }


    //  Getters and Setters

    public Integer getReviewD() {
        return ReviewID;
    }

    public void setReviewD(Integer reviewD) {
        ReviewID = reviewD;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getReplyToReview() {
        return ReplyToReview;
    }

    public void setReplyToReview(int replyToReview) {
        ReplyToReview = replyToReview;
    }

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int locationID) {
        LocationID = locationID;
    }

<<<<<<< HEAD
=======
    public ReviewsModel()
    {

    }


    public ReviewsModel( int ID, String content, int rating, int Location_Id, Time time, int ReplyToReview)
    {
       this.ReviewID = ReviewID;
       this.ID= ID;
       this.content = content;
       this.rating = rating;
       this.LocationID = Location_Id;
       this.time = time;
       this.ReplyToReview = ReplyToReview;
    }


>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac
}

