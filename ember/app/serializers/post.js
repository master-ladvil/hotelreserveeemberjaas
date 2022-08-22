import DS from 'ember-data';

export default DS.RESTSerializer.extend({
  normalizeResponse(store, primaryModelClass, payload, id, requestType) {
    console.log('control at serializer->post->normalize');
    payload = { posts: payload };
    console.log(payload);
    return this._super(store, primaryModelClass, payload, id, requestType);
  },
});
