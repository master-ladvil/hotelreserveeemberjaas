import { module, test } from 'qunit';
import { setupTest } from 'embu/tests/helpers';

module('Unit | Controller | roomselect', function (hooks) {
  setupTest(hooks);

  // TODO: Replace this with your real tests.
  test('it exists', function (assert) {
    let controller = this.owner.lookup('controller:roomselect');
    assert.ok(controller);
  });
});
