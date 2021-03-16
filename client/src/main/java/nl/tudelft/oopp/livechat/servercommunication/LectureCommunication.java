package nl.tudelft.oopp.livechat.servercommunication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nl.tudelft.oopp.livechat.data.Lecture;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Class for Lecture server communication.
 */
public class LectureCommunication {

    /**
     * Client object for sending requests.
     */
    private static final HttpClient client = HttpClient.newBuilder().build();

    /**
     * Gson object for parsing Json
     * set to parse fields according to annotations
     * and with specified date format.
     */
    private static final Gson gson = new GsonBuilder().setDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss zzz").excludeFieldsWithoutExposeAnnotation().create();

    /**
     * Creates a new lecture.
     * @param name the name of the lecture
     * @return  Lecture object if it was created successfully, null otherwise
     */
    // TODO I AM PASSING A BLANK STRING IN THE POST METHOD, THIS SHOULD BE CHANGED
    public static Lecture createLecture(String name) {
        //Encoding the lecture name into url compatible format
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);

        //Parameters for request
        HttpRequest.BodyPublisher req = HttpRequest.BodyPublishers.ofString("");
        String address = "http://localhost:8080/api/newLecture?name=";

        //Creating request and defining response
        HttpRequest request = HttpRequest.newBuilder().POST(req)
                .uri(URI.create(address + name)).build();
        HttpResponse<String> response;

        //Catching error when communicating with server
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }

        if (response.statusCode() != 200) {
            System.out.println("Status received: " + response.statusCode());
            return null;
        }

        //Return object from response
        return gson.fromJson(response.body(), Lecture.class);
    }

    /**
     * Sends an HTTP request to get a lecture by its uuid.
     * @param lectureId The uuid of the lecture
     * @return Lecture object if the lecture exists on server, or null if it doesn't
     */
    public static Lecture joinLectureById(String lectureId) {
        //Encoding the lecture id into url compatible format
        lectureId = URLEncoder.encode(lectureId, StandardCharsets.UTF_8);

        //Parameter for request
        String address = "http://localhost:8080/api/get/";

        //Creating request and defining response
        HttpRequest request = HttpRequest.newBuilder().GET().uri(
                URI.create(address + lectureId)).build();
        HttpResponse<String> response;

        //Catching error when communicating with server
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return null;
        }

        //Return object from response
        return gson.fromJson(response.body(), Lecture.class);
    }

    /**
     * Validate moderator.
     * @param lectureId the lecture id
     * @param modKey the moderator key
     * @return true if the moderator has been validated successfully,
     *         false otherwise
     */
    public static boolean validateModerator(String lectureId, String modKey) {
        //Encoding the lecture id and modKey into url compatible format
        lectureId = URLEncoder.encode(lectureId, StandardCharsets.UTF_8);
        modKey = URLEncoder.encode(modKey, StandardCharsets.UTF_8);

        //Parameter for request
        String address = "http://localhost:8080/api/validate/";

        //Creating request and defining response
        HttpRequest request = HttpRequest.newBuilder().GET().uri(
                URI.create(address + lectureId + "/" + modKey)).build();

        HttpResponse<String> response;

        //Catching error when communicating with server
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            return false;
        }

        return response.body().equals("0");

    }

    /**
     * Close lecture.
     * @return true if the lecture has been successfully closed by the server
     *         false otherwise
     */
    public static boolean closeLecture(String uuid, String modkey) {
        //Check if current lecture has been set
        if (Lecture.getCurrentLecture() == null) {
            System.out.println("You are not connected to a lecture!");
            return false;
        }

        HttpRequest.BodyPublisher req = HttpRequest.BodyPublishers.ofString("");

        HttpRequest request = HttpRequest.newBuilder().PUT(req).uri(
                URI.create("http://localhost:8080/api/close/" + uuid
                        + "/" + modkey)).build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
            System.out.println(response.body());
            return false;
        }

        return  response.body().equals("0");
    }

}
