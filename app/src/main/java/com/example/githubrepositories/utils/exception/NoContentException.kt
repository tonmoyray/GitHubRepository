package com.example.githubrepositories.utils.exception

import java.io.IOException
/**
 * <h1>NoContentException</h1>
 * <p>
 * Custom Exception class for when server returns no content but successful response
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class NoContentException(message:String) : IOException(message)