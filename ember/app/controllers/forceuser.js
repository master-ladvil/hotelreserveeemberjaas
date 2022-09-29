import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';
import Ember from 'ember';
import Service from '@ember/service';

export default class ForceuserController extends Controller {
  @action
  getuser() {
    $.ajax({
      url: 'https://login.salesforce.com/services/oauth2/token?grant_type=password&client_id=3MVG9fe4g9fhX0E64jBI8eK6ijF3vMhbiWQdW.qsT096F7OZ3dVI7If3uRGSIWhtXpHGukZqBwi9m5.FhYuTq&client_secret=29AC3659875FBB59D88189C96471FBE97F579DDCC8F723091B5B58B7224CC9EB&username=ladvil@ladvil.com&password=14mDk0r10NWCYQ8RGwWubcBJN3baWwI9gof',
      method: 'POST',
      dataType: 'json',
      success: function (response) {
        console.log(response.access_token);
        var accesstoken = response.access_token;
        $.ajax({
          url: 'https://zoho-c2-dev-ed.develop.my.salesforce.com/services/data/v54.0/query?q=Select+Id+,+FirstName+From+User',
          method: 'GET',

          beforeSend: function (xhr) {
            xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
            xhr.setRequestHeader('Access-Control-Allow-Credentials', 'true');
            xhr.setRequestHeader(
              'Access-Control-Allow-Methods',
              'GET,HEAD,OPTIONS,POST,PUT'
            );
            xhr.setRequestHeader(
              'Access-Control-Allow-Headers',
              'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            );
            xhr.setRequestHeader('Authorization', 'Bearer ' + accesstoken);
          },
          success: function (response) {
            console.log(response);
          },
          error: (xhr, status, error) =>
            console.log(`${status}: ${xhr.statusText}`),
        });
      },
      error: (xhr, status, error) =>
        console.log(`${status}: ${xhr.statusText}`),
    });
  }
}
