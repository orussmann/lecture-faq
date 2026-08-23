<#import "../base-layout-lecturer.ftlh" as base>

<@base.layout>
    <#if notification??>
        <div class="alert alert-info max-w-2xl mx-auto mb-4">
            <span>${notification}</span>
        </div>
    </#if>

    <div class="card bg-base-100 shadow-xl max-w-2xl mx-auto mb-8">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">Polls</h2>
            <ul class="space-y-3">
                <#list polls as poll>
                    <li class="card bg-base-200">
                        <div class="card-body p-4">
                            <h3 class="font-bold text-lg">${poll.title}</h3>
                            <p class="text-sm opacity-70">${poll.description}</p>
                            <div class="card-actions justify-end mt-2">
                                <a href="/app/user/lecturer/polls/${poll.id}" class="btn btn-sm btn-outline">Details</a>
                                <a href="/app/user/lecturer/polls/${poll.id}/results" class="btn btn-sm btn-primary">See
                                    results</a>
                            </div>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>
    </div>

    <div class="card bg-base-100 shadow-xl max-w-2xl mx-auto">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">Multiple-Choice Fragen stellen</h2>
            <form action="/app/user/lecturer/poll-form" method="post" class="space-y-4">

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Titel</span>
                    </label>
                    <input type="text" name="title" placeholder="Titel" class="input input-bordered w-full" required/>
                    <#if errors?? && errors.getFieldError("title")??>
                        <label class="label">
                            <span class="label-text-alt text-error">${errors.getFieldError("title")["defaultMessage"]}</span>
                        </label>
                    </#if>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Beschreibung (Optional)</span>
                    </label>
                    <input type="text" name="description" placeholder="Beschreibung..."
                           class="input input-bordered w-full"/>
                    <#if errors?? && errors.getFieldError("description")??>
                        <label class="label">
                            <span class="label-text-alt text-error">${errors.getFieldError("description")["defaultMessage"]}</span>
                        </label>
                    </#if>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Antwort A</span>
                    </label>
                    <input type="text" name="answers[0].text" placeholder="Antwort A"
                           class="input input-bordered w-full"/>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Antwort B</span>
                    </label>
                    <input type="text" name="answers[1].text" placeholder="Antwort B"
                           class="input input-bordered w-full"/>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Antwort C</span>
                    </label>
                    <input type="text" name="answers[2].text" placeholder="Antwort C"
                           class="input input-bordered w-full"/>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Antwort D</span>
                    </label>
                    <input type="text" name="answers[3].text" placeholder="Antwort D"
                           class="input input-bordered w-full"/>
                </div>
                <#if errors?? && errors.getFieldError("answers")??>
                    <label class="label">
                        <span class="label-text-alt text-error">${errors.getFieldError("answers")["defaultMessage"]}</span>
                    </label>
                </#if>

                <div class="form-control mt-6">
                    <button type="submit" class="btn btn-primary w-full">Abschicken</button>
                </div>
            </form>
        </div>
    </div>
</@base.layout>