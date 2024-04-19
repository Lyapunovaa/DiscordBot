package apiClient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

public class DTO_ART_RESP {

    //Responses

    //First
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseGenerateArtDto {
        private String id;
        private boolean done;
        /*private  String description= "";
        private  String createdAt = "";
        private  String createdBy= "";
        private  String modifiedAt = "";
        private  String metadata = "";*/

        //error dto
        private String error;
       /* private  String message;
        private  List<String> details;
        private  int code;*/

    }


    //Second
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetImageArtDTO {
        private String id;
        private boolean done;
        private DTO_ART_RESP.ResponseGetImageArtDTO response;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseGetImageArtDTO {
        private String image;
    }

}
