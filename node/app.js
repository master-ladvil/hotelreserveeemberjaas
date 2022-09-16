const express = require('express')
const app = express()
const bp = require('body-parser')
var request = require('request');
const { default: axios } = require('axios');
const jwt = require('json-web-token')
const jwtd= require('jwt-decode')
const cors = require('cors');

app.use(cors());
var corsOptions = {
    origin: '*',
}


const port = 3000
var token = "";


app.get('/', (req, res) => {
  var code = req.query.authcode;
  var clientid = req.query.clientid;
  var scope = req.query.scope;
  var clientsecret = "eyJhbGciOiJSUzI1NiIsInR5cGUiOiJKV1QifQ==.eyJhdWQiOiIxOWU4ZWViNTQwOWY4OWIwZjhkZGI4YjMzYjcxNTEwZCIsImlzcyI6ImVsbG9hdXRoIiwiaWF0IjoxNjYzMjQ4OTkxfQ==.VIOUBVTKGprxzhJcjAgtF5ikRPf7Q7IUSZpCisy9pVVA5VT6QtbOYHqRma-X5qHNjKlbmkgEKQGYLiCCfRn5qSZrlDl540PBPtvE1vRGHJ2DhvNZgEC0vaolkSE-ewoDlUEBnzQpXXtK_KBxW2Y_LqeaJY9u0NESH5DLN3GFGAZoUfYGxqMZgn_TiWx-O5MQ0C8Ggzd40kimx47g1XHfZrhMYTNVCCa5SWSJObRBpULNJdIZVkEPOzrjLzhBkMbNh5ZGEvsx5qYAmP-gOrHzAgRWd_NcjdsoQo-F0tJPrzJfM5iPI8Ofu7ah19eaE-mDnzEWFvs6oCxLIGGl8-PjNA==";
  var idtok = ''
  var uri = 'http://localhost:8080/lorduoauth/Token?authcode='+code+'&redirectUri=http://localhost:3000&clientid='+clientid+'&clientsecret='+clientsecret+'&scope='+scope
  console.log(uri)
  axios({
      method:'get',
      url: uri,
  }).then(function(response){
      var idtok = response.data.idtoken
      var actoken = response.data.accesstoken;
      token = idtok
      console.log("\nidtoken -> "+idtok+"\n\n\naccesstoken -> "+actoken+"\n\n");
      axios({
          method:'post',
          url:'http://localhost:8085/hotelres/TokenExchange?token='+idtok+'&actoken='+actoken,
      }).then(function(response){
          console.log("Data----->"+response.data)
          if(response.data == 0){
              res.writeHead(301,{
                  "Location":"http://localhost:4200/error"  
              })
              return res.end()
          }else{
            res.writeHead(301,{
                "Location":"http://localhost:4200/admin"
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

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})