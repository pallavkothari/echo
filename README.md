# echo
echoes http request headers (sits behind an oauth2_proxy)

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

```
# install oauth2_proxy locally
brew install go
go get github.com/pallavkothari/oauth2_proxy


# NOTE : replace placeholder values below with real values!

# set up local env 
  # Hints:
  # provider : facebook, github, etc
  # app : https://peaceful-river-83867.herokuapp.com
  # cookie domain: peaceful-river-83867.herokuapp.com

cat <<EOF > .env
PORT=4180
OAUTH2_PROXY_CLIENT_ID=...
OAUTH2_PROXY_CLIENT_SECRET=...
OAUTH2_PROXY_COOKIE_SECRET=...
PROVIDER=...
APP=...
COOKIE_DOMAIN=...
OAUTH2_PROXY_SIGNATURE_KEY=sha1:supersecret
EOF


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

