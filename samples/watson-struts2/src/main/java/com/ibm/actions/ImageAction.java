
 // (C) Copyright IBM Corporation 2017, 2017

 // Licensed under the Apache License, Version 2.0 (the "License");
 // you may not use this file except in compliance with the License.
 // You may obtain a copy of the License at

 //      http://www.apache.org/licenses/LICENSE-2.0

 // Unless required by applicable law or agreed to in writing, software
 // distributed under the License is distributed on an "AS IS" BASIS,
 // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 // See the License for the specific language governing permissions and
 // limitations under the License.

package com.ibm.actions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.beans.ImgDetail;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.DetectedFaces;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualRecognitionOptions;

public class ImageAction {
	
	private ImgDetail img;
	String FACEAPIKEY;

	public ImageAction(){
		FACEAPIKEY= System.getenv("FACEAPIKEY");
	}
	public ImgDetail getImg() {
		return img;
	}


	public void setImg(ImgDetail img) {
		this.img = img;
	}

	//method for detecting faces
	public String execute(){
		try{
			System.out.println(img.getUrl());
			VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
			System.out.println(FACEAPIKEY);
			service.setApiKey(FACEAPIKEY);
			System.out.println("Detect faces");
			VisualRecognitionOptions options = new VisualRecognitionOptions.Builder().url(img.getUrl()).build();
			DetectedFaces result = service.detectFaces(options).execute();
			System.out.println(result);

			JsonObject rawOutput = new JsonParser().parse(result.toString()).getAsJsonObject();
			JsonObject face = rawOutput.get("images").getAsJsonArray().get(0).getAsJsonObject().get("faces")
					.getAsJsonArray().get(0).getAsJsonObject();

			if (face.get("identity") == null)
				img.setName("Cannot be identified");
			else
				img.setName(face.get("identity").getAsJsonObject().get("name").getAsString());

			if (face.get("gender") == null)
				img.setGender("Cannot be identified");
			else
				img.setGender(face.get("gender").getAsJsonObject().get("gender").getAsString());

			if (face.get("age").getAsJsonObject().get("min") == null)
				img.setMin_age("NA");
			else
				img.setMin_age(face.get("age").getAsJsonObject().get("min").getAsString());

			if (face.get("age").getAsJsonObject().get("max") == null)
				img.setMax_age("NA");
			else
				img.setMax_age(face.get("age").getAsJsonObject().get("max").getAsString());

			return "success";
			}
			catch(Exception e){
				e.printStackTrace();
				return "error";
			}
		
	}

}
