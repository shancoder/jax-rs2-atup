package org.feuyeux.jaxrs2.atup.cases.resource;

import org.feuyeux.jaxrs2.atup.cases.service.AtupTestResultService;
import org.feuyeux.jaxrs2.atup.core.constant.AtupApi;
import org.feuyeux.jaxrs2.atup.core.info.AtupErrorCode;
import org.feuyeux.jaxrs2.atup.core.info.AtupErrorInfo;
import org.feuyeux.jaxrs2.atup.core.info.AtupTestResultListInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Component
@Path(AtupApi.TEST_RESULT_PATH)
public class AtupTestResultResource {
    private final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(AtupTestCaseResource.class.getName());
    @Autowired
    AtupTestResultService service;

    @GET
    @Path("results")
    @Produces(MediaType.APPLICATION_JSON)
    public AtupTestResultListInfo getResultsByUser(@Context final HttpHeaders headers, @QueryParam("start") final Integer start,
                                                   @QueryParam("size") final Integer size) {
        log.debug("GET results getResultsByUser start = " + start + " size = " + size);
        final String userId = headers.getRequestHeader("Atup-User").get(0);
        if (start == null || size == null) {
            return new AtupTestResultListInfo(AtupErrorInfo.INVALID_PARAM, AtupErrorCode.INVALID_PARAM);
        }
        return service.getResults(Integer.valueOf(userId), start, size);
    }
}
