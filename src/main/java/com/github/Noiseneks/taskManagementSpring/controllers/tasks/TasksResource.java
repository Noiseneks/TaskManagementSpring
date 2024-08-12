package com.github.Noiseneks.taskManagementSpring.controllers.tasks;

import com.github.Noiseneks.taskManagementSpring.core.tasks.TasksModel;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.CommentDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.IdDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.TaskDto;
import com.github.Noiseneks.taskManagementSpring.domain.entity.Comment;
import com.github.Noiseneks.taskManagementSpring.domain.entity.Task;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksResource {

    private final TasksModel tasksModel;

    @Autowired
    public TasksResource(TasksModel tasksModel) {
        this.tasksModel = tasksModel;
    }


    @Operation(summary = "Create task",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task successfully created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Task.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto) {
        Task task = tasksModel.createTask(taskDto);
        return ResponseEntity.ok(task);
    }


    @Operation(summary = "Delete existing task",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task successfully deleted",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Task with given ID doesn't exist",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @DeleteMapping("/deleteTask")
    public ResponseEntity<HashMap<String, String>> deleteTask(@RequestBody TaskDto taskDto) {

        tasksModel.deleteTask(taskDto);

        HashMap<String, String> body = new HashMap<>();
        body.put("message", "Task deleted successfully");
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Edit task",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task successful updated",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Task.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PatchMapping("/editTask")
    public ResponseEntity<Task> editTask(@RequestBody TaskDto taskDto) {
        Task task = tasksModel.updateTask(taskDto);
        return ResponseEntity.ok(task);
    }


    @Operation(summary = "Get list of tasks where performer is you",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Task.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping("/getMyTasks")
    public ResponseEntity<List<Task>> getMyTasks() {
        List<Task> tasks = tasksModel.getTaskListForCurrentUser();
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Get list of tasks by given author id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Task.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping("/getByAuthorId")
    public ResponseEntity<List<Task>> getByAuthorId(@RequestBody IdDto idDto) {
        List<Task> tasks = tasksModel.getByAuthorId(idDto);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Get list of tasks by given author id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Task.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping("/getByPerformerId")
    public ResponseEntity<List<Task>> getByPerformerId(@RequestBody IdDto idDto) {
        List<Task> tasks = tasksModel.getByPerformerId(idDto);
        return ResponseEntity.ok(tasks);
    }


    @Operation(summary = "Get list of comments by given task id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Comment.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping("/getComments")
    public ResponseEntity<List<Comment>> getComments(@RequestBody IdDto taskIdDto) {
        List<Comment> comments = tasksModel.getCommentsByTaskId(taskIdDto);
        return ResponseEntity.ok(comments);
    }


    @Operation(summary = "Add comment to existing task",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comment successfully added",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDto commentDto) {
        Comment comment = tasksModel.addComment(commentDto);
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "Edit existing comment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comment successfully edited",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Task.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PatchMapping("/editComment")
    public ResponseEntity<Comment> editComment(@RequestBody CommentDto commentDto) {
        Comment comment = tasksModel.editComment(commentDto);
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "Delete existing comment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comment successfully deleted",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Comment with given ID doesn't exist",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
    @DeleteMapping("/deleteComment")
    public ResponseEntity<HashMap<String, String>> deleteComment(@RequestBody IdDto commentIdDto) {

        tasksModel.deleteComment(commentIdDto);

        HashMap<String, String> body = new HashMap<>();
        body.put("message", "Comment deleted successfully");
        return ResponseEntity.ok(body);
    }

}
