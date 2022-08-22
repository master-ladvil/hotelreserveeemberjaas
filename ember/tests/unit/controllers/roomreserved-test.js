import { module, test } from 'qunit';
import { setupTest } from 'embu/tests/helpers';

module('Unit | Controller | roomreserved', function (hooks) {
  setupTest(hooks);

  // TODO: Replace this with your real tests.
  test('it exists', function (assert) {
    let controller = this.owner.lookup('controller:roomreserved');
    assert.ok(controller);
  });
});
