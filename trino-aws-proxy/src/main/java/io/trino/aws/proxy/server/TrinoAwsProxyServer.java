/*
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
package io.trino.aws.proxy.server;

import com.google.common.collect.ImmutableList;
import com.google.inject.Module;
import io.airlift.bootstrap.Bootstrap;
import io.airlift.http.server.HttpServerModule;
import io.airlift.jaxrs.JaxrsModule;
import io.airlift.jmx.JmxHttpModule;
import io.airlift.jmx.JmxModule;
import io.airlift.json.JsonModule;
import io.airlift.log.LogJmxModule;
import io.airlift.log.Logger;
import io.airlift.node.NodeModule;
import io.airlift.openmetrics.JmxOpenMetricsModule;
import org.weakref.jmx.guice.MBeanModule;

public final class TrinoAwsProxyServer
{
    private static final Logger log = Logger.get(TrinoAwsProxyServer.class);

    private TrinoAwsProxyServer() {}

    public static void main(String[] args)
    {
        ImmutableList.Builder<Module> modules = ImmutableList.<Module>builder()
                .add(new TrinoAwsProxyServerModule())
                .add(new NodeModule())
                .add(new HttpServerModule())
                .add(new JsonModule())
                .add(new JaxrsModule())
                .add(new JmxModule())
                .add(new JmxHttpModule())
                .add(new JmxOpenMetricsModule())
                .add(new LogJmxModule())
                .add(new MBeanModule());

        Bootstrap app = new Bootstrap(modules.build());
        app.initialize();

        log.info("======== SERVER STARTED ========");
    }
}
