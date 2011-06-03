/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math.geometry.euclidean.oned;

import org.apache.commons.math.geometry.Vector;
import org.apache.commons.math.geometry.partitioning.Hyperplane;
import org.apache.commons.math.geometry.partitioning.Region;
import org.apache.commons.math.geometry.partitioning.SubHyperplane;

/** This class represents a 1D oriented hyperplane.
 * <p>An hyperplane in 1D is a simple point, its orientation being a
 * boolean.</p>
 * <p>Instances of this class are guaranteed to be immutable.</p>
 * @version $Id:$
 * @since 3.0
 */
public class OrientedPoint implements Hyperplane<Euclidean1D> {

    /** Vector location. */
    private Vector1D location;

    /** Orientation. */
    private boolean direct;

    /** Simple constructor.
     * @param location location of the hyperplane
     * @param direct if true, the plus side of the hyperplane is towards
     * abscissas greater than {@code location}
     */
    public OrientedPoint(final Vector1D location, final boolean direct) {
        this.location = location;
        this.direct   = direct;
    }

    /** Copy the instance.
     * <p>Since instances are immutable, this method directly returns
     * the instance.</p>
     * @return the instance itself
     */
    public OrientedPoint copySelf() {
        return this;
    }

    /** {@inheritDoc} */
    public double getOffset(final Vector<Euclidean1D> point) {
        final double delta = ((Vector1D) point).getX() - location.getX();
        return direct ? delta : -delta;
    }

    /** Build a region covering the whole hyperplane.
     * <p>Since this class represent zero dimension spaces which does
     * not have lower dimension sub-spaces, this method returns a dummy
     * implementation of a {@link Region Region} (always the same
     * instance). This implementation is only used to allow the {@link
     * SubHyperplane SubHyperplane} class implementation to work
     * properly, it should <em>not</em> be used otherwise.</p>
     * @return a dummy region
     */
    public SubOrientedPoint wholeHyperplane() {
        return new SubOrientedPoint(this, null);
    }

    /** Build a region covering the whole space.
     * @return a region containing the instance (really an {@link
     * IntervalsSet IntervalsSet} instance)
     */
    public IntervalsSet wholeSpace() {
        return new IntervalsSet();
    }

    /** Check if the instance has the same orientation as another hyperplane.
     * <p>This method is expected to be called on parallel hyperplanes
     * (i.e. when the {@link #side side} method would return {@link
     * org.apache.commons.math.geometry.partitioning.Hyperplane.Side#HYPER}
     * for some sub-hyperplane having the specified hyperplane
     * as its underlying hyperplane). The method should <em>not</em>
     * re-check for parallelism, only for orientation, typically by
     * testing something like the sign of the dot-products of
     * normals.</p>
     * @param other other hyperplane to check against the instance
     * @return true if the instance and the other hyperplane have
     * the same orientation
     */
    public boolean sameOrientationAs(final Hyperplane<Euclidean1D> other) {
        return !(direct ^ ((OrientedPoint) other).direct);
    }

    /** Get the hyperplane location on the real line.
     * @return the hyperplane location
     */
    public Vector1D getLocation() {
        return location;
    }

    /** Check if the hyperplane orientation is direct.
     * @return true if the plus side of the hyperplane is towards
     * abscissae greater than hyperplane location
     */
    public boolean isDirect() {
        return direct;
    }

    /** Revert the instance.
     */
    public void revertSelf() {
        direct = !direct;
    }

}