package org.launchcode.catfe.catfe.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;



@Entity
public class Cafe {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @OneToMany(mappedBy = "cafe",  cascade = CascadeType.ALL)
    @JsonIgnoreProperties("cafe")
    private Set<Food> food;
  
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"cafe", "userFavCats"})
    private Set<Cat> myCats;

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    @NotBlank(message = "Required")
    @Size(min = 3, max = 20, message = "Must be greater than 3 and less than 16 characters")
    private String username;


    @Size(min = 8, max = 16)
    private String password;


    @Size(min = 8, max = 16)
    private String verifyPassword;


    @NotBlank(message = "Required")
    @Size(min = 3, max = 50)
    private String cafeName;


    @NotBlank(message = "Required")
    @Size(min = 3, max = 250)
    private String streetAddress;


    @NotBlank(message = "Required")
    @Size(min = 2, max = 2)
    private String stateLocation;


    @NotBlank(message = "Required")
    @Size(min = 5, max = 5)
    private String zipCode;


    @NotBlank(message = "Required")
    @Size(min = 10, max = 25)
    private String phoneNum;


    @NotBlank(message = "Required")
    @Size(min = 3, max = 250)
    @Email
    private String emailAddress;

    private String instaLink;

    private String fbLink;

    private String twitterLink;

    @NotBlank(message = "Required")
    @Size(min = 3, max = 250)
    private String cafeDescription;

    @Size(max = 250)
    private String cafeRules;


    private String pwHash;

    private BigDecimal admissionPrice;

    private int avatar;

    @OneToMany(targetEntity = UserReview.class, mappedBy = "cafe", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("cafe")
    Set<UserReview> userReviews;

    private float lat;

    private float lng;

    private String place_id = "";

    public String getPlace_id() {

        return place_id;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    @ManyToMany(mappedBy = "favCafes")
    @JsonIgnoreProperties("favCats")
    private Set<User> userFavCafes;

    public int totalReviews;

    public int averageReview;



    public Cafe(String username, String password,
                String cafeName, String streetAddress,
                String stateLocation, String zipCode,
                String phoneNum, String emailAddress,
                String instaLink, String fbLink,
                String twitterLink, String cafeDescription,
                String cafeRules, BigDecimal admissionPrice) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.cafeName = cafeName;
        this.streetAddress = streetAddress;
        this.stateLocation = stateLocation;
        this.zipCode = zipCode;
        this.phoneNum = phoneNum;
        this.emailAddress = emailAddress;
        this.instaLink = instaLink;
        this.fbLink = fbLink;
        this.twitterLink = twitterLink;
        this.cafeDescription = cafeDescription;
        this.cafeRules = cafeRules;
        this.admissionPrice = admissionPrice;
        this.avatar = avatarSelect();
        this.averageReview = 5;
    }

    public int getAvatar() {
        return avatar;
    }


    private int avatarSelect() {
        Random r = new Random();
        int low = 0;
        int high = 20;
        int result = r.nextInt(high-low) + low;
        return result;
    }

    public Cafe() {
    }

    public String create_coords() throws IOException, ParseException {
        String url = createGoogleQuery();
        URL queryUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) queryUrl.openConnection();
        con.setRequestMethod("GET");

        StringBuilder fullResponseBuilder = new StringBuilder();

        fullResponseBuilder.append(con.getResponseCode())
                .append(" ")
                .append(con.getResponseMessage())
                .append("\n");

        con.getHeaderFields()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() != null)
                .forEach(entry -> {

                    fullResponseBuilder.append(entry.getKey())
                            .append(": ");

                    List<String> headerValues = entry.getValue();
                    Iterator<String> it = headerValues.iterator();
                    if (it.hasNext()) {
                        fullResponseBuilder.append(it.next());

                        while (it.hasNext()) {
                            fullResponseBuilder.append(", ")
                                    .append(it.next());
                        }
                    }

                    fullResponseBuilder.append("\n");
                });

        Reader streamReader = null;

        if (con.getResponseCode() > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        JSONParser json = new JSONParser(String.valueOf(content));
        LinkedHashMap<String, Object> jsonMap = json.parseObject();

        in.close();
        ArrayList<LinkedHashMap<String, Object>> results = (ArrayList<LinkedHashMap<String,Object>>)(jsonMap.get("results"));
        LinkedHashMap<String, Object> geometry = (LinkedHashMap<String, Object>) results.get(0).get("geometry");
        LinkedHashMap<String, BigDecimal> location = (LinkedHashMap<String, BigDecimal>) geometry.get("location");

        this.place_id = (String)results.get(0).get("place_id");
        this.lng = location.get("lng").floatValue();
        this.lat =  location.get("lat").floatValue();

        con.disconnect();

        return fullResponseBuilder.toString();

    }

    public String createGoogleQuery(){
        String urlInit = this.streetAddress + " " + this.stateLocation + " " + this.zipCode;
        urlInit =  urlInit.replaceAll(" ","%20");
        return "https://maps.googleapis.com/maps/api/geocode/json?address="+ urlInit + "&key=";
//        insert Google API key after &key=
    }


    @ElementCollection(targetClass=String.class)
    private Set <String> photos;

    public void addPhotos(String photoPath){
        this.photos.add(photoPath);
    }


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStateLocation() {
        return stateLocation;
    }

    public void setStateLocation(String stateLocation) {
        this.stateLocation = stateLocation;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getInstaLink() {
        return instaLink;
    }

    public void setInstaLink(String instaLink) {
        this.instaLink = instaLink;
    }

    public String getFbLink() {
        return fbLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getCafeDescription() {
        return cafeDescription;
    }

    public void setCafeDescription(String cafeDescription) {
        this.cafeDescription = cafeDescription;
    }

    public String getCafeRules() {
        return cafeRules;
    }

    public void setCafeRules(String cafeRules) {
        this.cafeRules = cafeRules;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public BigDecimal getAdmissionPrice() {
        return admissionPrice;
    }

    public void setAdmissionPrice(BigDecimal admissionPrice) {
        this.admissionPrice = admissionPrice;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public Set<Food> getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food.add(food);
    }
  
    public Set<UserReview> getUserReviews() {
        return userReviews;
    }

    public void setUserReview(UserReview userReview) {
        this.userReviews.add(userReview);
    }

    public Set<Cat> getMyCats() {
        return myCats;
    }

    public void setCat(Cat cat) {
        this.myCats.add(cat);
    }

    public Set<User> getUserFavCafes() {
        return userFavCafes;
    }

    public Set<String> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<String> photos) {
        this.photos = photos;
    }
    public int getTotalReviews() {
        return totalReviews;
    }

    public void addReview(UserReview userReview) {
        this.totalReviews += userReview.getUserRating();
        this.averageReview = Math.round((totalReviews) / (userReviews.size() + 1));
    }
    public int getAverageReview() {
        return averageReview;
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getVerifyPassword(), getCafeName(), getStreetAddress(), getStateLocation(), getZipCode(), getPhoneNum(), getEmailAddress(), getInstaLink(), getFbLink(), getTwitterLink(), getCafeDescription(), getCafeRules(), pwHash);
    }

}
