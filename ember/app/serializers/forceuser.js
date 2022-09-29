import RESTSerializer from '@ember-data/serializer/rest';
import Ember from 'ember';

export default class ForceuserSerializer extends RESTSerializer {
  normalizeResponse(store, primaryModelClass, payload, id, requestType) {
    console.log('control at serializer->post->normalize');
    payload = { forceuser: payload };
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
