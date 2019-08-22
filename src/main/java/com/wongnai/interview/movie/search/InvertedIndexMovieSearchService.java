package com.wongnai.interview.movie.search;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;

@Component("invertedIndexMovieSearchService")
@DependsOn("movieDatabaseInitializer")
public class InvertedIndexMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 4 => Please implement in-memory inverted index to search movie by keyword.
		// You must find a way to build inverted index before you do an actual search.
		// Inverted index would looks like this:
		// -------------------------------
		// |  Term      | Movie Ids      |
		// -------------------------------
		// |  Star      |  5, 8, 1       |
		// |  War       |  5, 2          |
		// |  Trek      |  1, 8          |
		// -------------------------------
		// When you search with keyword "Star", you will know immediately, by looking at Term column, and see that
		// there are 3 movie ids contains this word -- 1,5,8. Then, you can use these ids to find full movie object from repository.
		// Another case is when you find with keyword "Star War", there are 2 terms, Star and War, then you lookup
		// from inverted index for Star and for War so that you get movie ids 1,5,8 for Star and 2,5 for War. The result that
		// you have to return can be union or intersection of those 2 sets of ids.
		// By the way, in this assignment, you must use intersection so that it left for just movie id 5.
		Iterable<Movie> movieIterable = movieRepository.findAll();
		Map<Long, Movie> mapperMovie = new HashMap<>();
		Map<String, ArrayList<Long>> mapper = new HashMap<>();
		ArrayList arrayList;
		for (Movie movie: movieIterable
			 ) {
			mapperMovie.put(movie.getId(), new Movie(movie.getName(), movie.getActors()));
			for (String word : movie.getName().toLowerCase().split(" ")
				 ) {
				if (mapper.containsKey(word)){
					if (!mapper.get(word).contains(movie.getId())){
						mapper.get(word).add(movie.getId());
					}

				}
				else{
					arrayList = new ArrayList<>();
					arrayList.add(movie.getId());
					mapper.put(word, arrayList);
				}

			}

		}
		//counting
		List<Movie> result = new ArrayList<>();
		String[] words = queryText.toLowerCase().split(" ");
		int max = words.length;
		Map<Long,Integer> idsMap = new HashMap<>();
		for (String word : words
			 ) {
			if (mapper.get(word)!=null){
				for (Long ids : mapper.get(word)
				) {
					if (idsMap.containsKey(ids)){
						idsMap.replace(ids,idsMap.get(ids)+1);
					}
					else {
						idsMap.put(ids, 1);
					}
				}
			}
		//summary
		}
		Movie movie;
		for (Long id : idsMap.keySet()
			 ) {
			if (idsMap.get(id).equals(max)){
				movie = mapperMovie.get(id);
				result.add(new Movie(movie.getName(), movie.getActors()));
			}

		}
		return result;
	}
}
