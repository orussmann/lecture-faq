<#import "../base-layout-lecturer.ftlh" as base>

<@base.layout>
    <#if notification??>
        <div class="alert alert-info max-w-2xl mx-auto mb-4">
            <span>${notification}</span>
        </div>
    </#if>

    <div class="card bg-base-100 shadow-xl max-w-2xl mx-auto mb-8">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">Lectures</h2>
            <ul class="space-y-3">
                <#list allLectures as lecture>
                    <li class="card bg-base-200">
                        <div class="card-body p-4">
                            <h3 class="font-bold text-lg">${lecture.title}</h3>
                            <div class="text-sm space-y-1">
                                <p><strong>Typ:</strong> ${lecture.type}</p>
                                <p><strong>Beschreibung:</strong> ${lecture.description}</p>
                                <p><strong>Link:</strong> <a href="${lecture.link}" class="link link-primary">${lecture.link}</a></p>
                                <p><strong>Vorlesungscode:</strong> ${lecture.code?c}</p>
                            </div>
                            <div class="card-actions justify-end mt-2">
                                <a href="/app/user/lecturer/lectures/${lecture.id}" class="btn btn-sm btn-primary">Details</a>
                            </div>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>
    </div>


    <div class="card bg-base-100 shadow-xl max-w-2xl mx-auto">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">Neue Lecture erstellen</h2>
            <form id="lecture-form" action="/app/user/lectures" method="post" class="space-y-4">

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Lecture title</span>
                    </label>
                    <input type="text" placeholder="FDDW" name="title" id="title" minlength="1" maxlength="100" required class="input input-bordered w-full" />
                    <#if errors?? && errors.getFieldError("title")??>
                        <label class="label">
                            <span class="label-text-alt text-error">${errors.getFieldError("title")["defaultMessage"]}</span>
                        </label>
                    </#if>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Lecture description</span>
                    </label>
                    <input type="text" placeholder="A course that focuses on web services" name="description" id="description"
                           minlength="1" maxlength="1000" required class="input input-bordered w-full" />
                    <#if errors?? && errors.getFieldError("description")??>
                        <label class="label">
                            <span class="label-text-alt text-error">${errors.getFieldError("description")["defaultMessage"]}</span>
                        </label>
                    </#if>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Lecture type</span>
                    </label>
                    <select name="type" id="type" required class="select select-bordered w-full">
                        <option value="ONSITE">OnSite</option>
                        <option value="ONLINE">Online</option>
                        <option value="HYBRID">Hybrid</option>
                    </select>
                    <#if errors?? && errors.getFieldError("type")??>
                        <label class="label">
                            <span class="label-text-alt text-error">${errors.getFieldError("type")["defaultMessage"]}</span>
                        </label>
                    </#if>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Lecture link</span>
                    </label>
                    <input type="url"
                           placeholder="https://test.com"
                           name="link"
                           id="link"
                           minlength="1"
                           maxlength="1000"
                           required
                           class="input input-bordered w-full"
                    />
                    <#if errors?? && errors.getFieldError("link")??>
                        <label class="label">
                            <span class="label-text-alt text-error">${errors.getFieldError("link")["defaultMessage"]}</span>
                        </label>
                    </#if>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Lecture code</span>
                    </label>
                    <input type="number" placeholder="123" name="code" id="code" min="1" max="999" required class="input input-bordered w-full" />
                    <#if errors?? && errors.getFieldError("code")??>
                        <label class="label">
                            <span class="label-text-alt text-error">${errors.getFieldError("code")["defaultMessage"]}</span>
                        </label>
                    </#if>
                </div>

                <div class="form-control mt-6">
                    <button type="submit" id="submit-button" class="btn btn-primary w-full">Create lecture</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        let notification = document.getElementById("notification")

        if (notification){
            setTimeout(() => {
                notification.hidden = true;
            }, 3000)
        }

        document.addEventListener("DOMContentLoaded", () => {
            const form = document.querySelector("#lecture-form");

            form.addEventListener("submit", (event) => {
                event.preventDefault();

                const ws = new WebSocket(
                    "/app/new-lecture?lecturerId=${userId}"
                );

                ws.onopen = () => {
                    const title = document.querySelector("#title").value;

                    ws.send(title);

                    form.submit()
                };
            });
        });
    </script>
</@base.layout>
