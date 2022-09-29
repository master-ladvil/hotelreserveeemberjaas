import RESTSerializer from '@ember-data/serializer/rest';

export default class AsfSerializer extends RESTSerializer {
  normalizeResponse(store, primaryModelClass, payload, id, requestType) {
    console.log('control at serializer->post->normalize');
    payload = { asf: payload };
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
