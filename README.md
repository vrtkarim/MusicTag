
# Spring Boot API reading and manipulating music file metadata

A Spring Boot REST API for reading and manipulating music file metadata with support for all major ID3 tag versions (ID3v1, ID3v2.2, ID3v2.3, ID3v2.4) and other popular audio formats.


## API Reference

#### upload your music file

```http
  GET /api/music/upload
```


#### Get data

```http
  GET /api/music/getdata
```


#### Set data
body:
{
  "title": "Song Title",
  "comment": "This is a great song",
  "composer": "John Doe",
  "artist": "The Band Name",
  "album": "Amazing Album",
  "year": "2025",
  "genre": "Rock"
}

⚠️make sure to set all arguments, set unwanted ones to ""

```http
  POST /api/music/setdata
```
#### Get artwork

```http
  GET /api/music/getartwork
```
#### Set artwork

```http
  POST /api/music/setartwork
```
#### Set lyrics
⚠️body should be a form data as a text

```http
  POST /api/music/setlyrics
```
#### Get lyrics

```http
  Get /api/music/getlyrics
```
#### Get modified music file

```http
  GET /api/music/downloadmusic
```




## Installation

Install and run

```
  git clone https://github.com/vrtkarim/MusicTag.git

  cd MusicTag

  mvn spring-boot:run
```
    
