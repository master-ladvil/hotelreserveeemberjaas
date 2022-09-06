import RESTSerializer from '@ember-data/serializer/rest';
import Ember from 'ember';

export default class MySerializer extends RESTSerializer {
  normalizeResponse(store, primaryModelClass, payload, id, requestType) {
    console.log('control at serializer->post->normalize');
    payload = { My: payload };
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
