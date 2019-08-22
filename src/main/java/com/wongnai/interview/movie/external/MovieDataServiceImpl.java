package com.wongnai.interview.movie.external;

import com.google.common.collect.Streams;
import com.wongnai.interview.tool.json.JsonReader;
import org.assertj.core.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class MovieDataServiceImpl implements MovieDataService {
	public static final String MOVIE_DATA_URL
			= "https://raw.githubusercontent.com/prust/wikipedia-movie-data/master/movies.json";

	@Autowired
	private RestOperations restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public MoviesResponse fetchAll() {
		//TODO:
		// Step 1 => Implement this method to download data from MOVIE_DATA_URL and fix any error you may found.
		// Please noted that you must only read data remotely and only from given source,
		// do not download and use local file or put the file anywhere else.


		try {
			ArrayList<JSONObject> jsonObject = JsonReader.readJsonFromUrl(MOVIE_DATA_URL);
			MoviesResponse response = new MoviesResponse();
			MovieData movieData;
			for (JSONObject object : jsonObject) {
				movieData = new MovieData();
				movieData.setTitle(object.get("title").toString());
				movieData.setYear((int) object.get("year"));
				movieData.setGenres(JsonReader.changeStringToArrayList(object, "genres"));
				movieData.setCast(JsonReader.changeStringToArrayList(object, "cast"));
				response.add(movieData);
			}
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
//		new MoviesResponse()

		return null;
	}
}
