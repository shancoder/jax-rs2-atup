package org.feuyeux.jaxrs2.atup.device.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.jaxrs2.atup.core.constant.AtupApi;
import org.feuyeux.jaxrs2.atup.core.domain.AtupDevice;
import org.feuyeux.jaxrs2.atup.core.domain.AtupUser;
import org.feuyeux.jaxrs2.atup.core.info.AtupDeviceListInfo;
import org.feuyeux.jaxrs2.atup.device.service.AtupDeviceService;
import org.feuyeux.jaxrs2.atup.device.service.StationDetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@Path(AtupApi.DEVICE_PATH)
public class AtupDeviceResource {
    private final Logger log = LogManager.getLogger(AtupDeviceResource.class.getName());
    @Autowired
    private AtupDeviceService service;
    @Autowired
    private StationDetectService detectService;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AtupDevice updateDevice(@Context final HttpHeaders headers, final AtupDevice deviceInfo) {
        log.debug("PUT updateDevice headers = " + headers + " deviceInfo = " + deviceInfo);
        try {
            if (deviceInfo.getUser() == null) {
                fillUser(headers, deviceInfo);
            }
            return service.updateDevice(deviceInfo);
        } catch (final Exception e) {
            log.error(e);
            return null;
        }
    }

    private void fillUser(final HttpHeaders headers, final AtupDevice deviceInfo) {
        log.debug("do fillUser method!");
        final String userId = headers.getRequestHeader("Atup-User").get(0);
        final AtupUser currentUser = new AtupUser();
        currentUser.setUserId(Integer.valueOf(userId));
        deviceInfo.setUser(currentUser);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AtupDeviceListInfo getDevicesByUser(@Context final HttpHeaders headers) {
        log.debug("GET getDevicesByUser headers = " + headers);
        final String userId = headers.getRequestHeader("Atup-User").get(0);
        final List<AtupDevice> devices = service.getDeviceList(Integer.valueOf(userId));
        return new AtupDeviceListInfo(devices);
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public AtupDeviceListInfo getDevices() {
        log.debug("GET getDevices Path ALL");
        final List<AtupDevice> devices = service.getDeviceList();
        return new AtupDeviceListInfo(devices);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AtupDevice createDevice(@Context final HttpHeaders headers, final AtupDevice deviceInfo) {
        log.debug("POST createDevice deviceInfo = " + deviceInfo);
        try {
            fillUser(headers, deviceInfo);
            return service.createDevice(deviceInfo);
        } catch (final Exception e) {
            log.error(e);
            return null;
        }
    }

    @POST
    @Path("status")
    public void detect() throws ExecutionException, InterruptedException {
        log.debug("POST detect");
        detectService.detect();
    }
}
