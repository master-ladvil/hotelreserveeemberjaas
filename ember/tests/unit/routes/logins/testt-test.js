import { module, test } from 'qunit';
import { setupTest } from 'embu/tests/helpers';

module('Unit | Route | logins/testt', function (hooks) {
  setupTest(hooks);

  test('it exists', function (assert) {
    let route = this.owner.lookup('route:logins/testt');
    assert.ok(route);
  });
});
