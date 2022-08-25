import EmberRouter from '@ember/routing/router';
import config from 'embu/config/environment';

export default class Router extends EmberRouter {
  location = config.locationType;
  rootURL = config.rootURL;
}

Router.map(function () {
  this.route('logins');
  this.route('register');
  this.route('succcess');
  this.route('error');
  this.route('roomselect');
  this.route('roomreserved');
  this.route('admin');
  this.route('roomadded');
  this.route('embdtst');
  this.route('posts');
  this.route('my');
});
