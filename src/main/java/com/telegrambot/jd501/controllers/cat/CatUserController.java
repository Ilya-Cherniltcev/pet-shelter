package com.telegrambot.jd501.controllers.cat;



import com.telegrambot.jd501.exceptions.UserNotFoundException;
import com.telegrambot.jd501.model.cat.CatUser;
import com.telegrambot.jd501.service.cat_service.CatUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * class for work with CatUser
 * has CRUD operation
 */
@RestController
@RequestMapping("/cat/user")
public class CatUserController {
    private final CatUserService catUserService;

    public CatUserController(CatUserService catUserService) {
        this.catUserService = catUserService;
    }

    /**
     * get All CatUser-s from DataBase
     * Use method of CatUser service {@link CatUserService#getAllUsers()} (Collection<CatUser>)}
     *
     * @return collection of CatUser
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all CatUser",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection<CatUser> getAllUsers() {
        return catUserService.getAllUsers();
    }

    /**
     * add new CatUser in DataBase
     *
     * @param catUser Use method of Service {@link CatUserService#createUser(CatUser)}
     * @return CatUser
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new CatUser",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<CatUser> createUser(@RequestBody CatUser catUser) {
        return ResponseEntity.ok(catUserService.createUser(catUser));
    }

    /**
     * change CatUser in DataBase
     * Use method of Service {@link CatUserService#updateUser(CatUser)}
     *
     * @param catUser (object)
     * @return CatUser
     * @throws com.telegrambot.jd501.exceptions.UserNotFoundException if CatUser with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change CatUser By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CatUser not found"
            )
    })
    @PutMapping
    public ResponseEntity<CatUser> updateUser(@RequestBody CatUser catUser) {
        return ResponseEntity.ok(catUserService.updateUser(catUser));
    }

    /**
     * find CatUser by chatId and change  status of The Adopter,
     * add adopted Cat, Date of adoption, and set test day at 30 days
     * Use method User repository {@link }
     *
     * @param userChatId - catUser chatId for find catUser in repository,
     * @param petId  - pet id for find user in repository,
     * @return Changed User
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "find cat user by chatId and change him adoption status, add adoption Pet, adoption Date, and set test day at 30 days"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "When User or Cat not found"
            )
    })
    @PutMapping("/adoption/{userChatId}/{petId}")
    public CatUser changeStatusOfTheAdopter(@PathVariable Long userChatId, @PathVariable Long petId) {
        return catUserService.changeStatusOfTheAdopter(userChatId, petId);
    }

    /**
     * find CatUser by id and change amount of probation period
     *
     * @param chatId   - user chatId,
     * @param days - number of days to increase the term of the transfer
     * @return notification that probationary period has been extended (String)
     * or UserNotFoundException
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "find user by chatId and change amount of probation period"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "When User not found"
            )
    })
    @PutMapping("/change_period/{chatId}/{days}")
    public CatUser probationPeriodExtension(@PathVariable Long chatId, @PathVariable Integer days) {
        return catUserService.probationPeriodExtension(chatId, days);
    }


    /**
     * delete CatUser from DataBase by chatId
     * Use method of Service {@link CatUserService#deleteUser(Long)}
     *
     * @param chatId of CatUser
     * @return Deleted CatUser
     * @throws com.telegrambot.jd501.exceptions.UserNotFoundException if CatUser with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete CatUser By chatId",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @DeleteMapping("{chatId}")
    public ResponseEntity<CatUser> deleteUser(@PathVariable Long chatId) {
        return ResponseEntity.ok(catUserService.deleteUser(chatId));
    }
    /**
     * Use catUserService to Sent custom message to Cat User with chatId.
     * Use method CatUserService {@link CatUserService#sendMessageToUserWithChatId(Long, String)}
     * @param chatId person to send
     * @param message to send
     * @return String that a message has been sent to the user
     * @throws UserNotFoundException when user with chat id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sent message to cat user",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @PutMapping("/send_message_to_catUser/{chatId}/{message}")
    public String sendMessageToUserWithChatId(@PathVariable Long chatId, @PathVariable String message){
        return catUserService.sendMessageToUserWithChatId(chatId, message);
    }
    /**
     *
     * finds a user by chat id. changes him status. and sends him a message that he has passed the trial period
     * Use method CatUserService {@link CatUserService#changeStatusUserPassedProbationPeriod(Long)}
     * @param chatId of User
     * @return CatUser
     * @throws UserNotFoundException when user with chatId not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "user passed the trial period",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @PutMapping("/user_passed_probation_period/{chatId}")
    public CatUser changeStatusUserPassedProbationPeriod(@PathVariable Long chatId){
       return catUserService.changeStatusUserPassedProbationPeriod(chatId);
    }
    /**
     *
     * finds a user by chat id. changes him status. and sends him a message that he has not passed the trial period
     * Use method CatUserService {@link CatUserService#changeStatusUserNotPassedProbationPeriod(Long)}
     * @param chatId of CatUser
     * @return CatUser
     * @throws UserNotFoundException when user with chatId not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = " user not passed probation period",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatUser.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @PutMapping("/user_not_passed_probation_period/{chatId}")
    public CatUser changeStatusUserNotPassedProbationPeriod(@PathVariable Long chatId){
        return catUserService.changeStatusUserNotPassedProbationPeriod(chatId);
    }
}
