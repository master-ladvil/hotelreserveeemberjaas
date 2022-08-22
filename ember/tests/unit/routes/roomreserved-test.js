import { module, test } from 'qunit';
import { setupTest } from 'embu/tests/helpers';

module('Unit | Route | roomreserved', function (hooks) {
  setupTest(hooks);

  test('it exists', function (assert) {
    let route = this.owner.lookup('route:roomreserved');
    assert.ok(route);
  });
});
