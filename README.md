
# Spring Boot API reading and manipulating music file metadata

A Spring Boot REST API for reading and manipulating music file metadata with support for all major ID3 tag versions (ID3v1, ID3v2.2, ID3v2.3, ID3v2.4) and other popular audio formats.
## API Reference

#### upload your music

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
```http
  POST /api/music/setdata
```
#### SET data

```http
  POST /api/music/setartwork
```
#### Get Modified Music

```http
  GET /api/music/downloadmusic
```


## Infos
- API is still under development
- No Exceptions are handled
- only mp3 files are handled till now
- Other formats will be supported soon

