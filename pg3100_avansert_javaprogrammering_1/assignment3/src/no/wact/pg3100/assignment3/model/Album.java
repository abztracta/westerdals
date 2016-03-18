package no.wact.pg3100.assignment3.model;

/**
* @author Per Lauvås - lauper@nith.no
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class Album {

	private String title;
	private String artist;
	private String genre;
	private int tracks;
	private int releasedYear;

	public Album(String title, String artist, String genre, int tracks, int releasedYear) {
		setTitle(title);
		setArtist(artist);
		setGenre(genre);
		setTracks(tracks);
		setReleasedYear(releasedYear);

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title= title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getGenre() {
		return this.genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getTracks() {
		return tracks;
	}

	public void setTracks(int tracks) {
		this.tracks = tracks;
	}

	public int getReleasedYear() {
		return releasedYear;
	}

	public void setReleasedYear(int releasedYear) {
		this.releasedYear = releasedYear;
	}

	@Override
	public String toString() {
		return getArtist() + " (" + getTitle() + ") | " + tracks + " tracks | released in " + getReleasedYear();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Album && (obj == this || this.toString().equals(obj.toString()));
	}
}