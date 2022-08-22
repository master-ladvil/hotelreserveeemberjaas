import { module, test } from 'qunit';
import { setupTest } from 'embu/tests/helpers';

module('Unit | Controller | logins', function (hooks) {
  setupTest(hooks);

  // TODO: Replace this with your real tests.
  test('it exists', function (assert) {
    let controller = this.owner.lookup('controller:logins');
    assert.ok(controller);
  });
});
