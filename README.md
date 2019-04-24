# OAuth2 securing a Rest API

A REST API made with Spring Data Rest and secured by Spring Security and OAuth2. 

### Running the API

`./gradlew bootRun`

### Testing out

Request access token
```bash
curl -X POST \
  http://localhost:8080/oauth/token \
  -H 'Authorization: Basic YnJ1bm86YmVybmFyZG8=' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=password&username=admin%40test.com&password=123'
```

Refresh token
```bash
curl -X POST \
  http://localhost:8080/oauth/token \
  -H 'Authorization: Basic YnJ1bm86YmVybmFyZG8=' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=refresh_token&refresh_token=cc02cfbc-d502-47a5-a5ea-e18612d41b1d'
```

Check token
```bash
curl -X GET \
  'http://localhost:8080/oauth/check_token?token=a63abb12-3c71-4440-84b1-816125536e34' \
  -H 'Authorization: Basic YnJ1bm86YmVybmFyZG8='
```

Fetch data from secured resource
```bash
curl -X GET \
  http://localhost:8080/api/examples \
  -H 'Authorization: Bearer a63abb12-3c71-4440-84b1-816125536e34'
```

Create new record
```bash
curl -X POST \
  http://localhost:8080/api/examples \
  -H 'Authorization: Bearer a63abb12-3c71-4440-84b1-816125536e34' \
  -H 'Content-Type: application/json' \
  -d '{"name":"Test"}'
```

Update existing record
```bash
curl -X PATCH \
  http://localhost:8080/api/examples/1 \
  -H 'Authorization: Bearer a63abb12-3c71-4440-84b1-816125536e34' \
  -H 'Content-Type: application/json' \
  -d '{"name":"Test"}'
```

Delete existing record
```bash
curl -X DELETE \
  http://localhost:8080/api/examples/1 \
  -H 'Authorization: Bearer a63abb12-3c71-4440-84b1-816125536e34'
```
