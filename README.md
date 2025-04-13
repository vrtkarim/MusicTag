
# Spring Boot API reading and manipulating music file metadata





## Overview

A Spring Boot REST API for reading and manipulating music file metadata with support for all major ID3 tag versions (ID3v1, ID3v2.2, ID3v2.3, ID3v2.4) and other popular audio formats.

## Base URL

```
http://localhost:8080
```

## Core Endpoints

### Upload Music

Upload a music file to the server.

```
POST /api/music/upload
```

**Parameters:**
- `musicNamePath` (query, required): Path or name for the music file
- `file` (body, required): The music file to upload (binary)

**Response:** String confirmation

### Download Music

Download a music file from the server.

```
GET /api/music/downloadmusic
```

**Parameters:**
- `musicNamePath` (query, required): Path or name of the music file to download

**Response:** Binary file

### Metadata Operations

#### Get Metadata

Retrieve metadata for a music file.

```
GET /api/music/getdata
```

**Parameters:**
- `musicNamePath` (query, required): Path or name of the music file

**Response:** JSON object containing metadata

#### Set Metadata

Update metadata for a music file.

```
POST /api/music/setdata
```

**Parameters:**
- `musicNamePath` (query, required): Path or name of the music file
- Request body: JSON object with metadata fields

**Example Request Body:**
```json
{
  "title": "Song Title",
  "artist": "Artist Name",
  "album": "Album Name",
  "composer": "Composer Name",
  "genre": "Rock",
  "year": "2025",
  "comment": "This is a great song!"
}
```

**Response:** String confirmation

### Lyrics Operations

#### Get Lyrics

Retrieve lyrics for a music file.

```
GET /api/music/getlyrics
```

**Parameters:**
- `musicNamePath` (query, required): Path or name of the music file

**Response:** String containing lyrics

#### Set Lyrics

Update lyrics for a music file.

```
POST /api/music/setlyrics
```

**Parameters:**
- `musicNamePath` (query, required): Path or name of the music file
- `text` (query, required): The lyrics text

**Response:** String confirmation

### Artwork Operations

#### Get Artwork

Retrieve artwork for a music file.

```
GET /api/music/getartwork
```

**Parameters:**
- `musicNamePath` (query, required): Path or name of the music file

**Response:** Binary image data

#### Set Artwork

Update artwork for a music file.

```
POST /api/music/setartwork
```

**Parameters:**
- `musicNamePath` (query, required): Path or name of the music file
- `imageNamePath` (query, required): Path or name for the image file
- `file` (body, required): The image file to use as artwork (binary)

**Response:** Object confirmation

## Data Models

### Metadata Object (Data)

| Field | Type | Description |
|-------|------|-------------|
| title | string | Title of the song |
| artist | string | Name of the artist |
| album | string | Name of the album |
| composer | string | Name of the composer |
| genre | string | Music genre |
| year | string | Release year |
| comment | string | Additional comments |

- Maven or Gradle

### Building and Running

1. Clone the repository
2. Navigate to the project root directory
3. Build the project:
   ```bash
   ./mvnw clean install
   ```
4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## API Documentation

For detailed API documentation, you can access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues and feature requests, please file an issue on this repository.
