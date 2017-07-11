# echo
echoes http request headers (sits behind an oauth2_proxy)

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

```
# install oauth2_proxy locally
brew install go
go get github.com/pallavkothari/oauth2_proxy


# NOTE : replace placeholder values below with real values!

# set up local env 
echo "PORT=4180" >> .env
echo "OAUTH2_PROXY_CLIENT_ID=..." >> .env
echo "OAUTH2_PROXY_CLIENT_SECRET=..." >> .env
echo "OAUTH2_PROXY_COOKIE_SECRET=..." >> .env        ### e.g. secretsecretcookie0
echo "PROVIDER=..." >> .env     ######## e.g. facebook, github, etc. 
echo "APP=..." >> .env             ######## e.g. https://peaceful-river-83867.herokuapp.com
echo "COOKIE_DOMAIN=..." >> .env    ##### e.g. localhost, app.herokuapp.com, etc. 
echo "OAUTH2_PROXY_SIGNATURE_KEY=sha1:supersecret" >> .env # change me


# start it up locally as it would on heroku
source .env
echo "oauth2_proxy  --http-address=http://:$PORT --cookie-secure=false --cookie-domain=${COOKIE_DOMAIN} --provider="${PROVIDER}" --email-domain="*" --redirect-url=${APP}/oauth2/callback --set-xauthrequest=true --pass-access-token=true --upstream=http://127.0.0.1:8080" > start-oauth2-proxy.sh

chmod +x start-oauth2-proxy.sh

heroku local 
```

## heroku setup 
```
heroku create 
heroku buildpacks:add https://github.com/pallavkothari/heroku-buildpack-oauth2-proxy
heroku buildpacks:add heroku/java
cat .env | xargs heroku config:set 
git push heroku master 
heroku open 
```

