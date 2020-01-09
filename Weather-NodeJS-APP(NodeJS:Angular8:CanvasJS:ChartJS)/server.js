const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const path = require('path');
const cors = require('cors');
const https = require('https');
const otherreq = require('request')
var port = process.env.PORT ||8081;
app.use(cors());
app.use(bodyParser.json());
app.get('/city', function (req, resp) {
  otherreq('https://maps.googleapis.com/maps/api/place/autocomplete/json?input=Los&types=(cities)&language=en&key=AIzaSyDG2d7za71WnJc-6Iml9efnTXAsRpaxcu4', function(err, res,body){
    resp.send(JSON.parse(res.body));
  });
});
app.use(express.static(path.join(__dirname, 'dist/weather-app')));
app.get('*', function(req, res) {
    res.sendFile(path.join(__dirname, 'dist/weather-app/index.html'));
});


app.post('/daily',function(req, res){
  // console.log("daily_api_fetched)",req.body);
  var daily_url = "https://api.darksky.net/forecast/e1d34d255f7fee875672f104fa3ef317/"+req.body.lat+","+req.body.lon+","+req.body.time;
  // console.log(daily_url);
  https.get(daily_url,(autocomp) =>{
        let search_data = '';
        autocomp.on('data', (chunk) => {
              search_data += chunk;
          });
        autocomp.on('end',()=>{
          res.status(200).send(search_data);
      });
    });
});

app.post('/autocomplete',function(req, res){
  // console.log("Autocomplete_called",req.body);
  var auto_url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+req.body.city+"&types=(cities)&language=en&key=AIzaSyDG2d7za71WnJc-6Iml9efnTXAsRpaxcu4";
  // console.log(auto_url);
  https.get(auto_url,(autocomp) =>{
        let search_data = '';
        autocomp.on('data', (chunk) => {
              search_data += chunk;
          });
        autocomp.on('end',()=>{
          res.status(200).send(search_data);
      });
    });
});

app.post('/images',function(req,res){
  customsearch_url = "https://www.googleapis.com/customsearch/v1?q="+req.body.city+"&cx=012575428586890062799:dmy2wbkvrwr&imgSize=huge&imgType=news&num=8&searchType=image&key=AIzaSyDG2d7za71WnJc-6Iml9efnTXAsRpaxcu4";
  console.log(req.body);
  https.get(customsearch_url, (resp_customsearh_obj) =>{
      let search_data = '';
      resp_customsearh_obj.on('data', (chunk) => {
            search_data += chunk;
        });
      resp_customsearh_obj.on('end',()=>{
        res.status(200).send(search_data);
      });
  });
});

//getting data with the street and city, needs to be replaced
app.post('/currentweather',function(req, res){
  var street, city, state;
  var latitude, longitude, forecast,state_seal_url;
  console.log(req.body);
  street = req.body.Street;
  city = req.body.City;
  if (req.body.CurLoc == true){
      state = req.body.state_curloc;
      latitude = req.body.lat;
      longitude = req.body.lon;
      var forecast_url = 'https://api.darksky.net/forecast/e1d34d255f7fee875672f104fa3ef317/'+latitude+','+longitude;
      console.log(forecast_url);
      https.get(forecast_url, (resp_forecast_obj) =>{
        let forecast_data = '';
        resp_forecast_obj.on('data', (chunk) => {
              forecast_data += chunk;
          });
        resp_forecast_obj.on('end',()=>{
          forecast = JSON.parse(forecast_data);
          // console.log(forecast);
          customsearch_url = "https://www.googleapis.com/customsearch/v1?q="+state+"%20State%20Seal&cx=012575428586890062799:dmy2wbkvrwr&imgSize=large&imgType=news&num=1&searchType=image&key=AIzaSyDG2d7za71WnJc-6Iml9efnTXAsRpaxcu4";
          https.get(customsearch_url, (resp_customsearh_obj) =>{
            let search_data = '';
            resp_customsearh_obj.on('data', (chunk) => {
                  search_data += chunk;
              });
            resp_customsearh_obj.on('end',()=>{
              state_seal_url = JSON.parse(search_data).items[0].link;
              // console.log(JSON.parse(search_data),state_seal_url);
              // console.log(forecast,{"darkskyapi":forecast,"state-seal":state_seal_url});
              res.status(200).send({"darkskyapi":forecast,"state-seal":state_seal_url});
          });
        });
    });
    }).on("error", (err) => {
    console.log("Error: " + err.message);
  });
  }
  else{ 
  state = req.body.State;
  var get_lat_lng = "https://maps.googleapis.com/maps/api/geocode/json?address="+street+","+city+","+state+"&key=AIzaSyDG2d7za71WnJc-6Iml9efnTXAsRpaxcu4" ;
  console.log(get_lat_lng);
  https.get(get_lat_lng, (resp) =>{
      let data = '';
      resp.on('data', (chunk) => {
          data += chunk;
      });
      resp.on('end', () => {
          other_data = data;
          latitude = JSON.parse(data).results[0].geometry.location.lat;
          longitude = JSON.parse(data).results[0].geometry.location.lng;
          var forecast_url = 'https://api.darksky.net/forecast/e1d34d255f7fee875672f104fa3ef317/'+latitude+','+longitude;
          console.log(forecast_url);
          https.get(forecast_url, (resp_forecast_obj) =>{
            let forecast_data = '';
            resp_forecast_obj.on('data', (chunk) => {
                  forecast_data += chunk;
              });
            resp_forecast_obj.on('end',()=>{
              forecast = JSON.parse(forecast_data);
              // console.log(forecast);
              customsearch_url = "https://www.googleapis.com/customsearch/v1?q="+state+"%20State%20Seal&cx=012575428586890062799:dmy2wbkvrwr&imgSize=huge&imgType=news&num=1&searchType=image&key=AIzaSyDG2d7za71WnJc-6Iml9efnTXAsRpaxcu4";
              https.get(customsearch_url, (resp_customsearh_obj) =>{
                let search_data = '';
                resp_customsearh_obj.on('data', (chunk) => {
                      search_data += chunk;
                  });
                resp_customsearh_obj.on('end',()=>{
                  state_seal_url = JSON.parse(search_data).items[0].link;
                  // console.log(JSON.parse(search_data),state_seal_url);

                  // console.log(forecast,{"darkskyapi":forecast,"state-seal":state_seal_url});
                  res.status(200).send({"darkskyapi":forecast,"state-seal":state_seal_url});
              });
            });
        });
      });
    });
  }).on("error", (err) => {
    console.log("Error: " + err.message);
  });
}
});

var server = app.listen(8081, function () {
   var host = server.address().address
   var port = server.address().port
   
   console.log("Example app listening at http://%s:%s", host, port)
});
