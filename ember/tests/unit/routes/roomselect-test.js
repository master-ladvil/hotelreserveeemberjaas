import { module, test } from 'qunit';
import { setupTest } from 'embu/tests/helpers';

module('Unit | Route | roomselect', function (hooks) {
  setupTest(hooks);

  test('it exists', function (assert) {
    let route = this.owner.lookup('route:roomselect');
    assert.ok(route);
  });
});
