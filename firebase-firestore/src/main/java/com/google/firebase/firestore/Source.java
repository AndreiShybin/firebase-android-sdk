// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.firebase.firestore;

import com.google.firebase.annotations.PublicApi;

/**
 * Configures the behavior of {@code get()} calls on {@link DocumentReference} and {@link Query}. By
 * providing a Source value, these methods can be configured to fetch results only from the server,
 * only from the local cache, or attempt to fetch results from the server and fall back to the cache
 * (which is the default).
 */
@PublicApi
public enum Source {
  /**
   * Causes Firestore to try to retrieve an up-to-date (server-retrieved) snapshot, but fall back to
   * returning cached data if the server can't be reached.
   */
  DEFAULT,

  /**
   * Causes Firestore to avoid the cache, generating an error if the server cannot be reached. Note
   * that the cache will still be updated if the server request succeeds. Also note that
   * latency-compensation still takes effect, so any pending write operations will be visible in the
   * returned data (merged into the server-provided data).
   */
  SERVER,

  /**
   * Causes Firestore to immediately return a value from the cache, ignoring the server completely
   * (implying that the returned value may be stale with respect to the value on the server). If
   * there is no data in the cache to satisfy the {@code get()} call, {@link
   * DocumentReference#get()} will return an error and {@link Query#get()} will return an empty
   * {@link QuerySnapshot} with no documents.
   */
  CACHE
}
