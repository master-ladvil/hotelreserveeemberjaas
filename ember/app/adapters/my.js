import RESTAdapter from '@ember-data/adapter/rest';

export default class MyAdapter extends RESTAdapter {
  host = 'http://localhost:8080/hotelres';

  pathForType() {
    console.log('inside adapter');
    return 'My';
  }
}
