const express = require('express')
const app = express()
const bp = require('body-parser')
var request = require('request');
const { default: axios } = require('axios');
const jwt = require('json-web-token')
const jwtd= require('jwt-decode')
const port = 3000
var token = "";

app.get('/', (req, res) => {
  var code = req.query.code
  var idtok = ''
  var uri = 'https://www.googleapis.com/oauth2/v4/token?code='+code+'&redirectUri=http://localhost:3000&client_id=632357853468-c9i98eg398g759brmg8nlbg9cu2h0b4i.apps.googleusercontent.com&client_secret=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&grant_type=authorization_code'
  console.log(uri)
  axios({
      method:'post',
      url: uri,
  }).then(function(response){
      var idtok = response.data.id_token
      token = idtok
      //console.log(idtok)
      axios({
          method:'post',
          url:'http://localhost:8080/hotelres/TokenExchange?token='+idtok,
      }).then(function(response){
          //console.log(response.data)
          if(response.data == 0){
              res.writeHead(301,{
                  "Location":"http://localhost:4200/error"
                  
              })
              return res.end()
          }else{
            res.writeHead(301,{
                "Location":"http://localhost:4200/adi"
            })
            return res.end()
          }
      }).catch(function(response){
          console.log(response)
      })
  }).catch(function(response){
      console.log(response);
  })
})
app.get('/jwt',(req,res)=>{
    var jwtt = req.query.tkn
    var decoded = jwtd(jwtt)
    console.log(decoded)
    res.send("hiki")
})
app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})