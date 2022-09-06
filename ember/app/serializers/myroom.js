import RESTSerializer from '@ember-data/serializer/rest';
import Ember from 'ember';

export default class MyroomSerializer extends RESTSerializer {
  normalizeResponse(store, primaryModelClass, payload, id, requestType) {
    console.log('Control at serializer myroom..');
    payload = { Myroom: payload };
    console.log(payload);
    return super.normalizeResponse(
      store,
      primaryModelClass,
      payload,
      id,
      requestType
    );
  }
}
