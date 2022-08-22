import { module, test } from 'qunit';
import { setupTest } from 'embu/tests/helpers';

module('Unit | Route | logins', function (hooks) {
  setupTest(hooks);

  test('it exists', function (assert) {
    let route = this.owner.lookup('route:logins');
    assert.ok(route);
  });
});
