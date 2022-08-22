import { module, test } from 'qunit';
import { setupTest } from 'embu/tests/helpers';

module('Unit | Controller | roomadded', function (hooks) {
  setupTest(hooks);

  // TODO: Replace this with your real tests.
  test('it exists', function (assert) {
    let controller = this.owner.lookup('controller:roomadded');
    assert.ok(controller);
  });
});
