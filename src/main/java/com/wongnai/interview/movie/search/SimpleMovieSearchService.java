package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.wongnai.interview.movie.external.MovieData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class
		Iterator<MovieData> movieDataIterator = movieDataService.fetchAll().iterator();
		List<Movie> movieList = new ArrayList<>();
		MovieData movieData;
		String title;
		while (movieDataIterator.hasNext()){
			movieData = movieDataIterator.next();
			title = movieData.getTitle().toLowerCase();
			queryText = queryText.toLowerCase();
			List<String> titleWords = Arrays.asList(title.split(" "));
			for (String word : titleWords
				 ) {
				if (!title.equals(queryText) && word.equals(queryText)){
					movieList.add(new Movie(movieData.getTitle(), movieData.getCast()));
				}
			}

		}
		return movieList;
	}
}
