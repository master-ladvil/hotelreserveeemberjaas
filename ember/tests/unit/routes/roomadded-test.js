import { module, test } from 'qunit';
import { setupTest } from 'embu/tests/helpers';

module('Unit | Route | roomadded', function (hooks) {
  setupTest(hooks);

  test('it exists', function (assert) {
    let route = this.owner.lookup('route:roomadded');
    assert.ok(route);
  });
});
