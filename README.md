##POSTMAN - POST REQUEST TO RECIVE OAUTH2 TOKEN

[URL] : "https://dev-ixblodb5nhesv8ts.us.auth0.com/oauth/token"

## HEADER KEY/VALUE

[content-type]: "application/json"

## BODY KEY/VALUE

[client_id] : "TFU9ovbad4Em55KmtGcOh10Bx6vM0S36"
[client_secret] : "urFOAfeDSM9AJNb_9qH65d-sc1XrwzMgW8BuFL-lswPwjgCilRJiGDERvd-s2z2L"
[audience] : "https://www.xoxoxo.com/"
[grant_type] : "client_credentials"

## RUN PROJECT - INSTRUCTIONS

1) Open your local system variables environment
2) Add new variable with following name : "AUDIENCE_CAR_SERVICE_ADMIN"
and following value : "https://www.xoxoxo.com/".
3) Add new variable with following name : "URI_CAR_SERVICE_ADMIN"
and following value : "https://dev-ixblodb5nhesv8ts.us.auth0.com/".

3) Run the project in your favourite IDEE
4) Open POSTMAN (any data you need you can find upper) : 
	4.1) Execute POST request on link [URL].
	4.2) Add HEADER/VALUE
	4.3) Add request Body (client_id,...)
5) Execute request.
6) Copy the token you recevied.
7) Enter this token as Bareer Token - Authorization to execute POST/PUT/DELETE requests.
8) Any GET request is free to be executed without token.
