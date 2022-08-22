import { module, test } from 'qunit';
import { setupTest } from 'embu/tests/helpers';

module('Unit | Controller | route', function (hooks) {
  setupTest(hooks);

  // TODO: Replace this with your real tests.
  test('it exists', function (assert) {
    let controller = this.owner.lookup('controller:route');
    assert.ok(controller);
  });
});
