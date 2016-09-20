/*
 * Copyright 2016 David Karnok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.akarnokd.rxjava2.debug;

import hu.akarnokd.rxjava2.debug.ObservableOnAssembly.OnAssemblyObserver;
import io.reactivex.*;
import io.reactivex.internal.fuseable.ScalarCallable;

/**
 * Wraps a ObservableSource and inject the assembly info.
 *
 * @param <T> the value type
 */
final class ObservableOnAssemblyScalarCallable<T> extends Observable<T> implements ScalarCallable<T> {

    final ObservableSource<T> source;

    final RxJavaAssemblyException assembled;

    ObservableOnAssemblyScalarCallable(ObservableSource<T> source) {
        this.source = source;
        this.assembled = new RxJavaAssemblyException();
    }

    @Override
    protected void subscribeActual(Observer<? super T> s) {
        source.subscribe(new OnAssemblyObserver<T>(s, assembled));
    }

    @SuppressWarnings("unchecked")
    @Override
    public T call() {
        return ((ScalarCallable<T>)source).call();
    }
}
